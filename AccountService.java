package com.zhiling.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zhiling.bank.dao.AccountMapper;
import com.zhiling.bank.pojo.Account;

@Service
public class AccountService {
	
	@Autowired
	AccountMapper dao;
	
	@CachePut(value = "redisCache",key = "'acc_'+#result.accno")
	public Account addAccount(Account account) {
		dao.insert(account);
		return account;
	}
	
	@Cacheable(value = "redisCache",key = "#userid+'-allcards'",condition = "#result != 'null'")
	public List<Account> findAccountByUserid(Integer userid) {
		
		return dao.findByUserid(userid);
	}
	
	@Cacheable(value = "redisCache",key = "#userid+'-cardcount'") 
	public int count(Integer userid) {
		
		return dao.count(userid);
	}
	
	
	
	
}
