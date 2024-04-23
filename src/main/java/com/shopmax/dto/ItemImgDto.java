package com.shopmax.dto;

import com.shopmax.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {

    private Long id;

    private String imgName; //UUID로 바뀐 이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 경로

    private String repImgYn; // 대표 이미지 여부(Y : 썸네일 이미지, N : 일반 이미지)

    //ModelMapper를 사용하기 위해 ModelMapper 객체를 만든다.
    private static ModelMapper modelMapper = new ModelMapper();

    //entity -> dto
    //static을 붙이면 객체를 생성하지 않아도 된다.
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class); //ItemImgDto 객체를 리턴
        //ItemImg entity를 ItemImgDto.class로 바꿔주겠다는 의미
    }
}
