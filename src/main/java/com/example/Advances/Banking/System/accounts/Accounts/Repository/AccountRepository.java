package com.example.Advances.Banking.System.accounts.Accounts.Repository;

import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

//repository هي الطبقة المسؤولة عن التعامل مع قاعدة البيانات CRUD
//لما منكتب JpaRepository يلي هية مكتبة ذكية فكانو عم خبر spring boot انو انت اتولى الامور كلها المسؤول عن الحسابات مثل
//save , findAll, findById,deleteBy
public interface AccountRepository extends JpaRepository<Account,Long>{


    }

