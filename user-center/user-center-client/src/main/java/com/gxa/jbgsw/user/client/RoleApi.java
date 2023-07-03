package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.RoleDTO;
import com.gxa.jbgsw.user.protocol.dto.RolePO;
import com.gxa.jbgsw.user.protocol.dto.RoleRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RoleApi {

    @PostMapping("/role/saveOrUpdate")
    public void saveOrUpdate(@RequestBody RoleDTO roleDTO);

    @PostMapping("/role/pageQuery")
    public PageResult<RolePO> pageQuery(@RequestBody RoleRequest request);

    @GetMapping("/role/getRoleById")
    public RolePO getDepartmentById(@RequestParam("id") Long id);

    @GetMapping("/role/delete")
    public void delete(@RequestParam("id") Long id);

    @PostMapping(value = "/role/deleteBatchIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);
}
