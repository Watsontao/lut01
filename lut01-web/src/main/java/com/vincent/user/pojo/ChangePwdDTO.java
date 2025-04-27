package com.vincent.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @@author vincent
 * @create2025-04-26-17:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwdDTO {

    private String oldPassword;
    private String newPassword;


}
