package com.example.wineydomain.user.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.preference.entity.Preference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "`User`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class User extends BaseEntity implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 아이디
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profileImgUrl")
    private String profileImgUrl;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "socialId")
    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType = SocialType.normal;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "level")
    private Integer level;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Preference preference;

    @Column(name = "role")
    private String role = UserRole.ROLE_USER.getValue();

    private boolean tasteNoteAnalysis = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String r : role.split(","))
            authorities.add(new SimpleGrantedAuthority(r));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public boolean getTasteNoteAnalysis(){
        return tasteNoteAnalysis;
    }

}
