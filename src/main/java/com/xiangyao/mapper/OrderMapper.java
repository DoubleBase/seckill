package com.xiangyao.mapper;

import com.xiangyao.domains.Order;
import com.xiangyao.domains.SeckillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

/**
 * @author xianggua
 * @description
 * @date 2019-7-21 18:20
 * @since 1.0
 */
@Repository
public interface OrderMapper {

    @Select("select * from miaosha_order where user_id = #{userNickName} and goods_id = #{goods_id}")
    SeckillOrder getSeckillOrderByUserIdAndGoodsId(@Param("userNickName") String userNickName,@Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{status},#{createDate} )")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insertOrder(Order order);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    void insertSeckillOrder(SeckillOrder seckillOrder);

    @Select("select * from order_info where id = #{orderId}")
    Order getOrderById(@Param("orderId") long orderId);
}
