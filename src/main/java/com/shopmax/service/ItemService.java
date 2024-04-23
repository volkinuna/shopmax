package com.shopmax.service;

import com.shopmax.dto.ItemFormDto;
import com.shopmax.entity.Item;
import com.shopmax.entity.ItemImg;
import com.shopmax.repository.ItemImgRepository;
import com.shopmax.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    //item 테이블에 상품 등록(insert)
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //1. 상품 등록(insert)
        Item item = itemFormDto.createItem(); //DTO -> entity로 바꿔서 item에 넣는다.
        itemRepository.save(item); //insert

        //2. 이미지 등록(최대 5개의 이미지를 등록해야하므로 for문으로 하나씩 저장)
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item); //★itemImg가 item을 참조하므로 insert 하기전 반드시 Item 객체를 넣어준다.
            
            //첫번째 이미지일때 대표 이미지로 지정
            if (i == 0) {
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }
            
            //이미지 파일을 하나씩 저장
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        
        return item.getId(); //등록한 상품의 id를 리턴
    }
}
