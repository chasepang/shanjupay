package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MerchantConvert {

    MerchantConvert INSTANCE = Mappers.getMapper(MerchantConvert.class);

    MerchantDTO entity2dto(Merchant entity);

    Merchant dto2entity(MerchantDTO dto);

    List<MerchantDTO> listentity2dto(List<MerchantDTO> merchantDTOList);

    public static void main(String[] args) {
        // dto --> entity
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setUsername("测试");
        merchantDTO.setPassword("111");
        Merchant entity = MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        // entity --> dto
        entity.setMobile("13589890981");
        MerchantDTO merchantDTO1 = MerchantConvert.INSTANCE.entity2dto(entity);
        System.out.println(merchantDTO1);
    }
}
