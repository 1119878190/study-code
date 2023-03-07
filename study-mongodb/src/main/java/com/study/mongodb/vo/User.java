package com.study.mongodb.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User implements Serializable {
    private static final long serialVersionUID = 4273617050148274838L;
    @Id
    private String id;
    private String account;
    private String name;
    private String ssn;
    private String email;
    private String password;
    private UserState state = UserState.NORMAL;
    private Date createDate;
    private String groupPath;
    private List<String> tableRoleNames;
    /**
     *
     */
    private String expiredDate;
    private Date lastLoginDate;
    private String organizationId;
    private List<String> roles;
    private String groupId;

    private String gender; // "male" | "female"
    private String mobileNumber;
    private String nickName;
    private String header;
    private boolean active = true;

    private boolean simulated;
    /**
     * 来源标识: 1 代表的是成功系统的用户信息 2 代表的是我方用户信息
     */
    private Integer sourceType;
    /**
     * 柳州jz 修改密码需求 md5 加密
     */
    private String cloudPlatformPassword;
    /**
     * 部门
     */
    private String department;
    /**
     * 部门编号
     */
    private String departmentNo;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 政治面貌
     */
    private String politicalStatus;
    /**
     * 民族
     */
    private String nation;
    /**
     * 籍贯
     */
    private String jiGuan;
    /**
     * 职务
     */
    private String station;

    /**
     * 警号
     */
    private String policeId;

    public String getCloudPlatformPassword() {
        return cloudPlatformPassword;
    }

    public void setCloudPlatformPassword(String cloudPlatformPassword) {
        this.cloudPlatformPassword = cloudPlatformPassword;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getJiGuan() {
        return jiGuan;
    }

    public void setJiGuan(String jiGuan) {
        this.jiGuan = jiGuan;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;

    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSimulated() {
        return simulated;
    }

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public List<String> getTableRoleNames() {
        return tableRoleNames;
    }

    public void setTableRoleNames(List<String> tableRoleNames) {
        this.tableRoleNames = tableRoleNames;
    }

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public static enum UserState {
        NORMAL, INACTIVE, CANCELLATION, DELETED;
    }
}
