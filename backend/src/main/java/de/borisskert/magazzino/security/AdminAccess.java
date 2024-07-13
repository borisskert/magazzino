package de.borisskert.magazzino.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAnyAuthority(T(de.borisskert.magazzino.security.Role).ADMIN.getAuthority())")
public @interface AdminAccess {
}
