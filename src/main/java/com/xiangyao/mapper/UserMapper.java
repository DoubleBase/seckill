package com.xiangyao.mapper;

import com.xiangyao.domains.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 18:16
 * @since 1.0
 */
public interface UserMapper {

    @Insert("insert into miaosha_user (id , nickname ,password , salt ,head,register_date,last_login_date)value (#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(User user);

    @Select("select * from miaosha_user where id = #{id}")
    User getUserById(@Param("id") long id);

    @Select("select * from miaosha_user where nickname = #{nickname}")
    User getUserByNickName(@Param("nickname") String nickname);
}
