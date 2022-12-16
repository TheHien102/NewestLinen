package com.example.newestlinen;

import com.cloudinary.Cloudinary;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Group;
import com.example.newestlinen.storage.model.Permission;
import com.example.newestlinen.utils.projection.repository.AccountRepository;
import com.example.newestlinen.utils.projection.repository.GroupRepository;
import com.example.newestlinen.utils.projection.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@Slf4j
public class NewestLinenApplication {

    @Autowired
    AccountRepository qrCodeStorageService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PermissionRepository permissionRepository;

    public static void main(String[] args) {
        SpringApplication.run(NewestLinenApplication.class, args);
    }

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private void createAdminUserIfNotExist() {
        Account account = qrCodeStorageService.findAccountByUsername("admin");
        if (account == null) {
            List<Permission> defaultPermission = addPermission();
            Group group = initGroupDefault(defaultPermission);

            account = new Account();
            account.setUsername("admin");
            account.setPassword(passwordEncoder.encode("admin123654"));
            account.setStatus(1);
            account.setKind(1);
            account.setFullName("Root account");
            account.setGroup(group);
            account.setIsSuperAdmin(true);
            qrCodeStorageService.save(account);
        }

    }

    private List<Permission> addPermission() {
        List<Permission> results = new ArrayList<>();
        Permission permissionCreateGroup = new Permission();
        permissionCreateGroup.setAction("/v1/group/create");
        permissionCreateGroup.setDescription("Create Group");
        permissionCreateGroup.setName("Create Group");
        permissionCreateGroup.setNameGroup("Group");
        permissionCreateGroup.setShowMenu(false);
        results.add(permissionRepository.save(permissionCreateGroup));

        Permission permissionViewGroup = new Permission();
        permissionViewGroup.setAction("/v1/group/get");
        permissionViewGroup.setDescription("View Group");
        permissionViewGroup.setName("View Group");
        permissionViewGroup.setNameGroup("Group");
        permissionViewGroup.setShowMenu(false);
        results.add(permissionRepository.save(permissionViewGroup));

        Permission permissionUpdateGroup = new Permission();
        permissionUpdateGroup.setAction("/v1/group/update");
        permissionUpdateGroup.setDescription("Update Group");
        permissionUpdateGroup.setName("Update Group");
        permissionUpdateGroup.setNameGroup("Group");
        permissionUpdateGroup.setShowMenu(false);
        results.add(permissionRepository.save(permissionUpdateGroup));


        Permission permissionCreatePermission = new Permission();
        permissionCreatePermission.setAction("/v1/permission/create");
        permissionCreatePermission.setDescription("Create Permission");
        permissionCreatePermission.setName("Create Permission");
        permissionCreatePermission.setNameGroup("Permission");
        permissionCreatePermission.setShowMenu(false);
        results.add(permissionRepository.save(permissionCreatePermission));

        return results;
    }

    private Group initGroupDefault(List<Permission> defaultPermission) {
        Group superAdminGroup = new Group();
        superAdminGroup.setKind(1);
        superAdminGroup.setName("ROLE SUPPER ADMIN");
        superAdminGroup.setId(1L);
        superAdminGroup.setPermissions(defaultPermission);
        return groupRepository.save(superAdminGroup);
    }

    @PostConstruct
    public void initialize() {
        createAdminUserIfNotExist();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
