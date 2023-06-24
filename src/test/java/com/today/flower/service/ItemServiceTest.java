package com.today.flower.service;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

import com.today.flower.ItemSellStatus;
import com.today.flower.storeitem.StoreItem;
import com.today.flower.storeitem.StoreItemFormDto;
import com.today.flower.storeitem.StoreItemImg;
import com.today.flower.storeitem.StoreItemImgRepository;
import com.today.flower.storeitem.StoreItemRepository;
import com.today.flower.storeitem.StoreItemService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application.properties")
class ItemServiceTest {

    @Autowired
    StoreItemService itemService;

    @Autowired
    StoreItemRepository itemRepository;

    @Autowired
    StoreItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "C:/flower/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        StoreItemFormDto itemFormDto = new StoreItemFormDto();
        itemFormDto.setItemNm("테스트상품");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setItemDetail("테스트 상품 입니다.");
        itemFormDto.setPrice(1000);
        itemFormDto.setStockNumber(100);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long itemId = itemService.saveStoreItem(itemFormDto, multipartFileList);
        List<StoreItemImg> itemImgList = itemImgRepository.findByStoreItemIdOrderByIdAsc(itemId);

        StoreItem item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDto.getItemNm(), item.getItemNm());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
    }
}