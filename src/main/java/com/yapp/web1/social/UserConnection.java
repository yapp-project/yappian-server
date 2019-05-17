package com.yapp.web1.social;

import com.yapp.web1.domain.BaseEntity;
import com.yapp.web1.domain.VO.SocialProviderType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Social에서 받아온 User 데이터 테이블
 *
 * @author Dakyung Ko
 */
@Entity
@Table(name = "user_connection")
@AttributeOverride(name="idx", column=@Column(name="user_connection_idx"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserConnection extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private SocialProviderType socialProviderType;

    @Column(name = "provider_idx", unique = true, nullable = false)
    private String providerIdx;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "expire_time")
    private long expireTime;

    @Builder
    private UserConnection(String email, SocialProviderType socialProviderType, String providerIdx, String displayName, String profileUrl, String imageUrl, String accessToken, long expireTime) {
        this.email = email;
        this.socialProviderType = socialProviderType;
        this.providerIdx = providerIdx;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.expireTime = expireTime;
    }

    public static UserConnection valueOf(GoogleUserDetails userDetails) {
        return UserConnection.builder()
                .expireTime(userDetails.getExpiration())
                .accessToken(userDetails.getAccess_token())
                .providerIdx(userDetails.getSub())
                .email(userDetails.getEmail())
                .displayName(userDetails.getName())
                .imageUrl(userDetails.getPicture())
                .socialProviderType(SocialProviderType.GOOGLE)
                .profileUrl(userDetails.getProfile())
                .build();
    }
}
