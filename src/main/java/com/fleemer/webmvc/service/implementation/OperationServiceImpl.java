package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.repository.OperationRepository;
import com.fleemer.webmvc.service.OperationService;
import com.fleemer.webmvc.service.exception.ServiceException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationServiceImpl extends AbstractService<Operation, Long, OperationRepository> implements OperationService {
    private final OperationRepository repository;

    @Autowired
    public OperationServiceImpl(OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OperationRepository getRepository() {
        return repository;
    }

    @Override
    public Iterable<Operation> findAll(Person person) {
        return repository.findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person);
    }

    @Override
    public <S extends Operation> S save(S entity) throws ServiceException {
        Account in = entity.getInAccount();
        Account out = entity.getOutAccount();
        Category category = entity.getCategory();
        checkLogicalConstraints(in, out, category);
        BigDecimal sum = entity.getSum();
        if (category == null) {
            in.setBalance(in.getBalance().add(sum));
            out.setBalance(out.getBalance().subtract(sum));
            return super.save(entity);
        }
        if (category.getType() == CategoryType.INCOME) {
            in.setBalance(in.getBalance().add(sum));
        }
        if (category.getType() == CategoryType.OUTCOME) {
            out.setBalance(out.getBalance().subtract(sum));
        }
        return super.save(entity);
    }

    private static void checkLogicalConstraints(Account in, Account out, Category cat) throws ServiceException {
        if (in == null & out == null) {
            throw new ServiceException("both the income account and outcome account are missing");
        }
        if (cat == null & (in == null || out == null)) {
            throw new ServiceException("the category and at least one account are missing");
        }
        if (cat == null) {
            return;
        }
        if ((in != null && cat.getType() != CategoryType.INCOME) || (out != null && cat.getType() != CategoryType.OUTCOME)) {
            throw new ServiceException("wrong category type for that operation: " + cat.getType());
        }
    }
}
