package com.mall.generator.model;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table admin_user
 */
public class AdminUser {
    @ApiModelProperty(value = "用户主键id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.id
     *
     * @return the value of admin_user.id
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.id
     *
     * @param id the value for admin_user.id
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.user_name
     *
     * @return the value of admin_user.user_name
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.user_name
     *
     * @param userName the value for admin_user.user_name
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.password
     *
     * @return the value of admin_user.password
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.password
     *
     * @param password the value for admin_user.password
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AdminUser other = (AdminUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbg.generated Wed Apr 28 15:32:26 CST 2021
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        return result;
    }
}