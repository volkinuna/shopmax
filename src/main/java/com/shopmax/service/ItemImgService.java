package com.shopmax.service;

import com.shopmax.entity.ItemImg;
import com.shopmax.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}") // C:/shop/item
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    /*
     이미지 저장
     1. 파일을 itemImgLocation에 저장(서버에 저장) -> FileService를 이용
     2. item_img 테이블에 이미지 정보 insert
    */
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        //1. 파일을 itemImgLocation에 저장(서버에 저장) -> FileService를 이용
        String oriImgName = itemImgFile.getOriginalFilename(); //파일 이름 -> 이미지1.jpg와 같이 가져온다.
        String imgName = "";
        String imgUrl = "";
        
        if (!StringUtils.isEmpty(oriImgName)) { //oriImgName이 빈문자열인지 아닌지 검사
            //oriImgName이 빈문자열이 아니라면 이미지 파일 업로드 진행
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            //itemImgFile.getBytes() : 이미지 파일을 byte 배열로 만들어준다. (스트림에서 사용하기 위해)

            imgUrl = "/images/item/" + imgName; // /images/item/SLDGUE-ALGOIT-LATNKA.jpg
        }

        //2. item_img 테이블에 이미지 정보 insert
        //DB에 insert 하기 전 유저가 직접 입력하지 못하는 값들은 개발자가 넣어준다.
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg); //insert
    }
}
