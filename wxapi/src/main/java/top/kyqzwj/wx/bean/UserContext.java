package top.kyqzwj.wx.bean;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:25
 */
public class UserContext {
    private String userId;
    private String userName;
    private String orgId;
    private String companyId;
    private String companyCode;
    private Integer isAdmin;
    private Integer isActive;
    private String userSex;
    private String userTel;
    private String userFax;
    private String userMobile;
    private String userMail;
    private String userQq;
    private String userWeixin;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String officeCode;
    private String token;
    private String clientId;

    public UserContext() {
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public Integer getIsAdmin() {
        return this.isAdmin;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public String getUserSex() {
        return this.userSex;
    }

    public String getUserTel() {
        return this.userTel;
    }

    public String getUserFax() {
        return this.userFax;
    }

    public String getUserMobile() {
        return this.userMobile;
    }

    public String getUserMail() {
        return this.userMail;
    }

    public String getUserQq() {
        return this.userQq;
    }

    public String getUserWeixin() {
        return this.userWeixin;
    }

    public String getAddress1() {
        return this.address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public String getAddress3() {
        return this.address3;
    }

    public String getAddress4() {
        return this.address4;
    }

    public String getOfficeCode() {
        return this.officeCode;
    }

    public String getToken() {
        return this.token;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setOrgId(final String orgId) {
        this.orgId = orgId;
    }

    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }

    public void setCompanyCode(final String companyCode) {
        this.companyCode = companyCode;
    }

    public void setIsAdmin(final Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setIsActive(final Integer isActive) {
        this.isActive = isActive;
    }

    public void setUserSex(final String userSex) {
        this.userSex = userSex;
    }

    public void setUserTel(final String userTel) {
        this.userTel = userTel;
    }

    public void setUserFax(final String userFax) {
        this.userFax = userFax;
    }

    public void setUserMobile(final String userMobile) {
        this.userMobile = userMobile;
    }

    public void setUserMail(final String userMail) {
        this.userMail = userMail;
    }

    public void setUserQq(final String userQq) {
        this.userQq = userQq;
    }

    public void setUserWeixin(final String userWeixin) {
        this.userWeixin = userWeixin;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public void setAddress3(final String address3) {
        this.address3 = address3;
    }

    public void setAddress4(final String address4) {
        this.address4 = address4;
    }

    public void setOfficeCode(final String officeCode) {
        this.officeCode = officeCode;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof UserContext)) {
            return false;
        } else {
            UserContext other = (UserContext)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label263: {
                    Object this$userId = this.getUserId();
                    Object other$userId = other.getUserId();
                    if (this$userId == null) {
                        if (other$userId == null) {
                            break label263;
                        }
                    } else if (this$userId.equals(other$userId)) {
                        break label263;
                    }

                    return false;
                }

                Object this$userName = this.getUserName();
                Object other$userName = other.getUserName();
                if (this$userName == null) {
                    if (other$userName != null) {
                        return false;
                    }
                } else if (!this$userName.equals(other$userName)) {
                    return false;
                }

                label249: {
                    Object this$orgId = this.getOrgId();
                    Object other$orgId = other.getOrgId();
                    if (this$orgId == null) {
                        if (other$orgId == null) {
                            break label249;
                        }
                    } else if (this$orgId.equals(other$orgId)) {
                        break label249;
                    }

                    return false;
                }

                Object this$companyId = this.getCompanyId();
                Object other$companyId = other.getCompanyId();
                if (this$companyId == null) {
                    if (other$companyId != null) {
                        return false;
                    }
                } else if (!this$companyId.equals(other$companyId)) {
                    return false;
                }

                label235: {
                    Object this$companyCode = this.getCompanyCode();
                    Object other$companyCode = other.getCompanyCode();
                    if (this$companyCode == null) {
                        if (other$companyCode == null) {
                            break label235;
                        }
                    } else if (this$companyCode.equals(other$companyCode)) {
                        break label235;
                    }

                    return false;
                }

                Object this$isAdmin = this.getIsAdmin();
                Object other$isAdmin = other.getIsAdmin();
                if (this$isAdmin == null) {
                    if (other$isAdmin != null) {
                        return false;
                    }
                } else if (!this$isAdmin.equals(other$isAdmin)) {
                    return false;
                }

                label221: {
                    Object this$isActive = this.getIsActive();
                    Object other$isActive = other.getIsActive();
                    if (this$isActive == null) {
                        if (other$isActive == null) {
                            break label221;
                        }
                    } else if (this$isActive.equals(other$isActive)) {
                        break label221;
                    }

                    return false;
                }

                label214: {
                    Object this$userSex = this.getUserSex();
                    Object other$userSex = other.getUserSex();
                    if (this$userSex == null) {
                        if (other$userSex == null) {
                            break label214;
                        }
                    } else if (this$userSex.equals(other$userSex)) {
                        break label214;
                    }

                    return false;
                }

                Object this$userTel = this.getUserTel();
                Object other$userTel = other.getUserTel();
                if (this$userTel == null) {
                    if (other$userTel != null) {
                        return false;
                    }
                } else if (!this$userTel.equals(other$userTel)) {
                    return false;
                }

                label200: {
                    Object this$userFax = this.getUserFax();
                    Object other$userFax = other.getUserFax();
                    if (this$userFax == null) {
                        if (other$userFax == null) {
                            break label200;
                        }
                    } else if (this$userFax.equals(other$userFax)) {
                        break label200;
                    }

                    return false;
                }

                label193: {
                    Object this$userMobile = this.getUserMobile();
                    Object other$userMobile = other.getUserMobile();
                    if (this$userMobile == null) {
                        if (other$userMobile == null) {
                            break label193;
                        }
                    } else if (this$userMobile.equals(other$userMobile)) {
                        break label193;
                    }

                    return false;
                }

                Object this$userMail = this.getUserMail();
                Object other$userMail = other.getUserMail();
                if (this$userMail == null) {
                    if (other$userMail != null) {
                        return false;
                    }
                } else if (!this$userMail.equals(other$userMail)) {
                    return false;
                }

                Object this$userQq = this.getUserQq();
                Object other$userQq = other.getUserQq();
                if (this$userQq == null) {
                    if (other$userQq != null) {
                        return false;
                    }
                } else if (!this$userQq.equals(other$userQq)) {
                    return false;
                }

                label172: {
                    Object this$userWeixin = this.getUserWeixin();
                    Object other$userWeixin = other.getUserWeixin();
                    if (this$userWeixin == null) {
                        if (other$userWeixin == null) {
                            break label172;
                        }
                    } else if (this$userWeixin.equals(other$userWeixin)) {
                        break label172;
                    }

                    return false;
                }

                Object this$address1 = this.getAddress1();
                Object other$address1 = other.getAddress1();
                if (this$address1 == null) {
                    if (other$address1 != null) {
                        return false;
                    }
                } else if (!this$address1.equals(other$address1)) {
                    return false;
                }

                Object this$address2 = this.getAddress2();
                Object other$address2 = other.getAddress2();
                if (this$address2 == null) {
                    if (other$address2 != null) {
                        return false;
                    }
                } else if (!this$address2.equals(other$address2)) {
                    return false;
                }

                label151: {
                    Object this$address3 = this.getAddress3();
                    Object other$address3 = other.getAddress3();
                    if (this$address3 == null) {
                        if (other$address3 == null) {
                            break label151;
                        }
                    } else if (this$address3.equals(other$address3)) {
                        break label151;
                    }

                    return false;
                }

                Object this$address4 = this.getAddress4();
                Object other$address4 = other.getAddress4();
                if (this$address4 == null) {
                    if (other$address4 != null) {
                        return false;
                    }
                } else if (!this$address4.equals(other$address4)) {
                    return false;
                }

                label137: {
                    Object this$officeCode = this.getOfficeCode();
                    Object other$officeCode = other.getOfficeCode();
                    if (this$officeCode == null) {
                        if (other$officeCode == null) {
                            break label137;
                        }
                    } else if (this$officeCode.equals(other$officeCode)) {
                        break label137;
                    }

                    return false;
                }

                Object this$token = this.getToken();
                Object other$token = other.getToken();
                if (this$token == null) {
                    if (other$token != null) {
                        return false;
                    }
                } else if (!this$token.equals(other$token)) {
                    return false;
                }

                Object this$clientId = this.getClientId();
                Object other$clientId = other.getClientId();
                if (this$clientId == null) {
                    if (other$clientId == null) {
                        return true;
                    }
                } else if (this$clientId.equals(other$clientId)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserContext;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        Object $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        Object $orgId = this.getOrgId();
        result = result * 59 + ($orgId == null ? 43 : $orgId.hashCode());
        Object $companyId = this.getCompanyId();
        result = result * 59 + ($companyId == null ? 43 : $companyId.hashCode());
        Object $companyCode = this.getCompanyCode();
        result = result * 59 + ($companyCode == null ? 43 : $companyCode.hashCode());
        Object $isAdmin = this.getIsAdmin();
        result = result * 59 + ($isAdmin == null ? 43 : $isAdmin.hashCode());
        Object $isActive = this.getIsActive();
        result = result * 59 + ($isActive == null ? 43 : $isActive.hashCode());
        Object $userSex = this.getUserSex();
        result = result * 59 + ($userSex == null ? 43 : $userSex.hashCode());
        Object $userTel = this.getUserTel();
        result = result * 59 + ($userTel == null ? 43 : $userTel.hashCode());
        Object $userFax = this.getUserFax();
        result = result * 59 + ($userFax == null ? 43 : $userFax.hashCode());
        Object $userMobile = this.getUserMobile();
        result = result * 59 + ($userMobile == null ? 43 : $userMobile.hashCode());
        Object $userMail = this.getUserMail();
        result = result * 59 + ($userMail == null ? 43 : $userMail.hashCode());
        Object $userQq = this.getUserQq();
        result = result * 59 + ($userQq == null ? 43 : $userQq.hashCode());
        Object $userWeixin = this.getUserWeixin();
        result = result * 59 + ($userWeixin == null ? 43 : $userWeixin.hashCode());
        Object $address1 = this.getAddress1();
        result = result * 59 + ($address1 == null ? 43 : $address1.hashCode());
        Object $address2 = this.getAddress2();
        result = result * 59 + ($address2 == null ? 43 : $address2.hashCode());
        Object $address3 = this.getAddress3();
        result = result * 59 + ($address3 == null ? 43 : $address3.hashCode());
        Object $address4 = this.getAddress4();
        result = result * 59 + ($address4 == null ? 43 : $address4.hashCode());
        Object $officeCode = this.getOfficeCode();
        result = result * 59 + ($officeCode == null ? 43 : $officeCode.hashCode());
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        Object $clientId = this.getClientId();
        result = result * 59 + ($clientId == null ? 43 : $clientId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UserContext(userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", orgId=" + this.getOrgId() + ", companyId=" + this.getCompanyId() + ", companyCode=" + this.getCompanyCode() + ", isAdmin=" + this.getIsAdmin() + ", isActive=" + this.getIsActive() + ", userSex=" + this.getUserSex() + ", userTel=" + this.getUserTel() + ", userFax=" + this.getUserFax() + ", userMobile=" + this.getUserMobile() + ", userMail=" + this.getUserMail() + ", userQq=" + this.getUserQq() + ", userWeixin=" + this.getUserWeixin() + ", address1=" + this.getAddress1() + ", address2=" + this.getAddress2() + ", address3=" + this.getAddress3() + ", address4=" + this.getAddress4() + ", officeCode=" + this.getOfficeCode() + ", token=" + this.getToken() + ", clientId=" + this.getClientId() + ")";
    }
}
