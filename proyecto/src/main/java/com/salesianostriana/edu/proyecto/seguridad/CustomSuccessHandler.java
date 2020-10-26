package com.salesianostriana.edu.proyecto.seguridad;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			System.out.println("Can't redirect");
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/*
	 * This method extracts the roles of currently logged-in user and returns
	 * appropriate URL according to his/her role.
	 */
	protected String determineTargetUrl(Authentication authentication) {
		String url = "";

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}

		if (isJefe(roles)) {
			url = "/jefe/principal";
		} else if (isProf(roles)) {
			url = "/profesor/principal";
		} else if (isAlumm(roles)) {
			url = "/alumno/principal";
		} else {
			url = "/acceso";
		}

		return url;
	}

	private boolean isJefe(List<String> roles) {
		if (roles.contains("ROLE_JEFE")) {
			return true;
		}
		return false;
	}

	private boolean isProf(List<String> roles) {
		if (roles.contains("ROLE_PROF")) {
			return true;
		}
		return false;
	}

	private boolean isAlumm(List<String> roles) {
		if (roles.contains("ROLE_ALUM")) {
			return true;
		}
		return false;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}


}
