package com.example.newestlinen.utils.projection.repository;

import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.TablePrefix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    public Account findAccountByUsername(String username);
    public Account findAccountByEmail(String email);
    public Account findByPhoneOrPhone(String phone, String adminPhone);
    public Long countAccountByPhone(String phone);
    public Long countAccountByPhoneOrUsername(String phone,String username);
    public Account findAccountByResetPwdCode(String resetPwdCode);
    public Account findAccountByEmailOrUsername(String email,String username);
    public Long countAccountByUsernameOrEmailOrPhone(String username, String email, String phone);
    public Page<Account> findAllByKind(int kind, Pageable pageable);
    public Long countAccountByUsername(String username);
    public Account findAccountByPhone(String phone);
    public Long countAccountByPhoneOrEmail(String phone, String email);
    public Long countAccountByEmail(String email);

    public Account findByUsernameOrEmailOrPhoneLike(String username, String email, String phone);

    Account findFirstById(Long id);

    void deleteByUsername(String username);

    @Query(value = "SELECT *" +
            " FROM " + TablePrefix.PREFIX_TABLE + "account a" +
            " WHERE a.kind = 3 OR a.kind = 4", nativeQuery = true)
    List<Account> findAllEmployeeAndCollaborator();

    List<Account> findAllByKind(Integer kind);

}
