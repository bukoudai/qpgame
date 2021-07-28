package com.bukoudai.qpgame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bukoudai.qpgame.entitys.Pets;
import org.apache.ibatis.annotations.Select;

public interface PetsMapper extends BaseMapper<Pets> {

    @Select("SELECT t1.pet_id,t1.pet_name,t1.pet_rarity,tp.type_name AS pet_type FROM `pets` AS t1 LEFT JOIN pet_type AS tp ON t1.pet_type=tp.pet_type_id JOIN (\n" +
            "SELECT ROUND(RAND()*((\n" +
            "SELECT MAX(pet_id) FROM `pets`)-(\n" +
            "SELECT MIN(pet_id) FROM `pets`))+(\n" +
            "SELECT MIN(pet_id) FROM `pets`)) AS pet_id) AS t2 WHERE t1.pet_id>=t2.pet_id ORDER BY t1.pet_id LIMIT 1;")
    Pets roundGetOne();
}
