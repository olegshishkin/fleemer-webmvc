package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.repository.OperationRepository;
import com.fleemer.webmvc.service.AccountService;
import com.fleemer.webmvc.service.CategoryService;
import com.fleemer.webmvc.service.OperationService;
import com.fleemer.webmvc.service.exception.ServiceException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationServiceImpl extends AbstractService<Operation, Long, OperationRepository>
        implements OperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    @Autowired
    public OperationServiceImpl(OperationRepository repository, AccountService accountService,
                                CategoryService categoryService) {
        this.operationRepository = repository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    protected OperationRepository getOperationRepository() {
        return operationRepository;
    }

    @Override
    public List<Operation> findAll(Person person) {
        return operationRepository.findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person);
    }

    @Override
    public List<Operation> findAll(Person person, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "date");
        return operationRepository.findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person,
                pageable);
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
            accountService.save(in);
            accountService.save(out);
            return super.save(entity);
        }
        if (category.getType() == CategoryType.INCOME) {
            in.setBalance(in.getBalance().add(sum));
        }
        if (category.getType() == CategoryType.OUTCOME) {
            out.setBalance(out.getBalance().subtract(sum));
        }
        if (in != null) {
            accountService.save(in);
        }
        if (out != null) {
            accountService.save(out);
        }
        categoryService.save(category);
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
