package com.fleemer.webmvc.web.form;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Operation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OperationForm {
    private static final String DATE_PATTERN = "p^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$" +
            "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$" +
            "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" +
            "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";

    @Valid
    private Operation operation;

    @NotNull
    @Pattern(regexp = OperationForm.DATE_PATTERN)
    private String date;

    private Account destination;
}
