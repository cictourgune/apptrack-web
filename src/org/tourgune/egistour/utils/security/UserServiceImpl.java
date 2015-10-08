package org.tourgune.egistour.utils.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.dao.DeveloperDao;
import org.tourgune.egistour.dao.VisorDao;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

	@Resource
	private DeveloperDao developerDao;
	@Resource
	private VisorDao visorDao;

	public UserDetails loadUserByUsername(String devname)
			throws UsernameNotFoundException {

		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

		String passwordDev = developerDao.getDeveloperPassword(devname);
		String passwordVisor = visorDao.getVisorPassword(devname);

		if (passwordDev != null) {

			AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_REGISTERED"));
			// Este objeto User es de Spring
			return new User(devname, passwordDev, AUTHORITIES);
		} else {
			if (passwordVisor != null) {

				AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_VISOR"));
				// Este objeto User es de Spring
				return new User(devname, passwordVisor, AUTHORITIES);
			} else {
				throw new UsernameNotFoundException("User not found: "
						+ devname);
			}

		}

	}
}
