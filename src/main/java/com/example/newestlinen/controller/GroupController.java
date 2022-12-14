package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ErrorCode;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.group.GroupAdminDto;
import com.example.newestlinen.dto.permission.PermissionDto;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.news.group.CreateGroupForm;
import com.example.newestlinen.form.news.group.UpdateGroupForm;
import com.example.newestlinen.mapper.GroupMapper;
import com.example.newestlinen.mapper.PermissionMapper;
import com.example.newestlinen.storage.criteria.GroupCriteria;
import com.example.newestlinen.storage.model.Group;
import com.example.newestlinen.storage.model.Permission;
import com.example.newestlinen.utils.projection.repository.GroupRepository;
import com.example.newestlinen.utils.projection.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/group")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class GroupController extends ABasicController {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<GroupAdminDto>> getList(GroupCriteria groupCriteria) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GROUP_ERROR_UNAUTHORIZED);
        }

        ApiMessageDto<ResponseListObj<GroupAdminDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Group> groupPage = groupRepository.findAll(groupCriteria.getSpecification(), Pageable.unpaged());

        ResponseListObj<GroupAdminDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(groupMapper.fromEntityListToAdminDtoList(groupPage.getContent()));

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List group success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list_combobox", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<GroupAdminDto>> getListCombox() {
        GroupCriteria groupCriteria = new GroupCriteria();
        ApiMessageDto<ResponseListObj<GroupAdminDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Group> groupPage = groupRepository.findAll(groupCriteria.getSpecification(), Pageable.unpaged());

        ResponseListObj<GroupAdminDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(groupMapper.fromEntityListToAdminDtoList(groupPage.getContent()));

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List group combobox success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateGroupForm createGroupForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new RequestException(ErrorCode.GROUP_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findFirstByName(createGroupForm.getName());
        if (group != null) {
            throw new RequestException(ErrorCode.GROUP_ERROR_EXIST);
        }
        group = new Group();
        group.setName(createGroupForm.getName());
        group.setDescription(createGroupForm.getDescription());
        group.setKind(createGroupForm.getKind());

        group.setStatus(1);
        group.setPermissions(permissionMapper.fromUpdateFormListToEntityList(createGroupForm.getPermissions()));
        groupRepository.save(group);
        apiMessageDto.setMessage("Create group success");
        return apiMessageDto;
    }

    @Transactional
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateGroupForm updateGroupForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new RequestException(ErrorCode.GROUP_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(updateGroupForm.getId()).orElse(null);
        if (group == null) {
            throw new RequestException(ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        //check su ton tai cua group name khac khi dat ten.
        Group otherGroup = groupRepository.findFirstByName(updateGroupForm.getName());
        if (otherGroup != null && !Objects.equals(updateGroupForm.getId(), otherGroup.getId())) {
            throw new RequestException(ErrorCode.GROUP_ERROR_EXIST);
        }
        group.setName(updateGroupForm.getName());
        group.setDescription(updateGroupForm.getDescription());

        group.setPermissions(permissionMapper.fromUpdateFormListToEntityList(updateGroupForm.getPermissions()));
        groupRepository.save(group);
        apiMessageDto.setMessage("Update group success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<GroupAdminDto> get(@PathVariable("id") Long id) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GROUP_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<GroupAdminDto> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(id).orElse(null);
        apiMessageDto.setData(groupMapper.fromEntityToGroupAdminDto(group));
        apiMessageDto.setMessage("Get group success");
        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        if (!isSuperAdmin()) {
            throw new RequestException(ErrorCode.GROUP_ERROR_UNAUTHORIZED);
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            throw new RequestException(ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        if (group.getKind() == 1) {
            throw new RequestException(ErrorCode.GROUP_ERROR_CAN_NOT_DELETED);
        }

        List<Permission> permissions = new ArrayList<>();
        group.setPermissions(permissions);
        groupRepository.save(group);
        groupRepository.deleteById(id);
        apiMessageDto.setMessage("Delete group success");
        return apiMessageDto;
    }
}
