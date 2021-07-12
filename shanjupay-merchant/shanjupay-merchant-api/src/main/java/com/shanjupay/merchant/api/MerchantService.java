package com.shanjupay.merchant.api;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.api.dto.StaffDTO;
import com.shanjupay.merchant.api.dto.StoreDTO;

public interface MerchantService {

    MerchantDTO queryMerchantById(Long id);

    /**
     * 商户注册
     *
     * @param merchantDTO
     * @return
     */
    MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException;


    /**
     * 资质申请
     *
     * @param merchantId
     * @param merchantDTO
     * @throws BusinessException
     */
    void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException;


    /**
     * 商户下新增门店
     *
     * @param storeDTO
     * @return
     * @throws BusinessException
     */
    StoreDTO createStore(StoreDTO storeDTO) throws BusinessException;


    /**
     * 商户添加员工
     *
     * @param staffDTO
     * @return
     * @throws BusinessException
     */
    StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException;


    /**
     * 为门店设置管理员
     *
     * @param storeId
     * @param staffId
     * @throws BusinessException
     */
    void bindStaffToStore(Long storeId, Long staffId) throws BusinessException;


    /**
     * 查询租户下的商户
     *
     * @param tenantId
     * @return
     */
    MerchantDTO queryMerchantByTenantId(Long tenantId) throws BusinessException;


}
