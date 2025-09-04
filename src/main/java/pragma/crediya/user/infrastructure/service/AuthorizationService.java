package pragma.crediya.user.infrastructure.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean canCreateUser(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("Administrador") ||
                        granted.getAuthority().equals("Asesor"));
    }

    public boolean canCreateRequest(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("Administrador") ||
                        granted.getAuthority().equals("Asesor") ||
                        granted.getAuthority().equals("Cliente"));
    }

    public boolean canListAllRequests(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("Administrador") ||
                        granted.getAuthority().equals("Asesor"));
    }

    public boolean canViewRequest(Authentication auth, String requestEmail) {
        boolean isAdminOrAdvisor = auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("Administrador") ||
                        granted.getAuthority().equals("Asesor"));
        if (isAdminOrAdvisor) return true;

        boolean isClient = auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("Cliente"));
        return isClient && auth.getName().equals(requestEmail);
    }

    public boolean canCreateRequestForEmail(Authentication auth, String requestEmail) {
        boolean isAdminOrAdvisor = auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("Administrador") ||
                        granted.getAuthority().equals("Asesor"));
        if (isAdminOrAdvisor) return true;

        boolean isClient = auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("Cliente"));
        return isClient && auth.getName().equals(requestEmail);
    }
}

