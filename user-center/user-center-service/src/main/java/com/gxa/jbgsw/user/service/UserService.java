package com.gxa.jbgsw.user.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.entity.User;
import com.gxa.jbgsw.user.protocol.dto.UpdateAdminPasswordDTO;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;

public interface UserService extends IService<User> {

    UserResponse getUserByCode(String code, Integer platform);

    void deleteBatchIds(Long[] ids);

    PageResult<UserResponse> pageQuery(UserRequest request);

    void add(User user);

    void updateUseStatus(Long id, Integer useStauts);

    void updateUser(UserDTO userDTO);

    UserResponse getUserByValidateCode(String mobile, String validateCode, Integer platform);

    void updatePassword(String mobile, String password);

    void updateUserAdmin(UserDTO userDTO);

    void updateAdminPassword(UpdateAdminPasswordDTO updateAdminPasswordDTO);
}