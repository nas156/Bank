package ua.bank.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.bank.project.entity.UserInfo;
import ua.bank.project.entity.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDTO {
    List<UserInfo> userInfos;
}
