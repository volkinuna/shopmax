package com.shopmax.dto;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력입니다.")
    private int price;

    @NotNull(message = "재고는 필수 입력입니다.")
    private int StockNumber;

    @NotBlank(message = "상품 상세 설명은 필수 입력입니다.")
    private String itemDetail;

    private ItemSellStatus itemSellStatus;
    
    //상품 1개당 최대 5개의 이미지를 저장하므로 아래와 같이 리스트로 작성
    //상품 이미지 정보를 저장
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    //상품 이미지 아이디들을 저장 -> 등록할땐 필요없으나, 수정시 이미지 아이디들을 담아둘 용도로 사용
    private List<Long> itemImgIds = new ArrayList<>();
    
    //ModelMapper를 사용하기 위해 ModelMapper 객체를 만든다.
    private static ModelMapper modelMapper = new ModelMapper();
    
    //dto -> entity
    public Item createItem() {
        return modelMapper.map(this, Item.class); //Item entity 객체 리턴
        //this는 ItemFormDto, ItemFormDto는 Item.class로 바꿔주겠다는 의미
    }
    
    //entity -> dto
    //static을 붙이면 객체를 생성하지 않아도 된다.
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class); //ItemFormDto 객체 리턴
        //Item entity를 ItemFormDto.class로 바꿔주겠다는 의미
    }
}
