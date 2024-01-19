package com.example.wineyapi.shop.service;

import static com.example.wineycommon.constants.WineyStatic.*;
import static com.example.wineydomain.shop.exception.GetShopErrorCode.*;

import com.example.wineyapi.shop.converter.ShopConverter;
import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineyapi.shop.service.context.ShopListContext;
import com.example.wineyapi.shop.service.context.ShopListContextFactory;
import com.example.wineycommon.exception.NotFoundException;
import com.example.wineydomain.shop.entity.BookMarkPk;
import com.example.wineydomain.shop.entity.Shop;
import com.example.wineydomain.shop.repository.ShopBookMarkRepository;
import com.example.wineydomain.shop.repository.ShopRepository;
import com.example.wineydomain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopConverter shopConverter;
    private final ShopBookMarkRepository shopBookMarkRepository;
    private final ShopRepository shopRepository;

    public List<ShopRes.ShopMapDto> getShopMapDtoList(ShopCommand.getMapCommandDTO commandDTO) {
        ShopListContext shopListContext = ShopListContextFactory.getShopListContext(commandDTO.getShopFilter());
        List<ShopRepository.ShopMapList> shopMapLists = shopListContext.getShopList(commandDTO);
        return shopConverter.toShopMapDtoLists(shopMapLists);
    }

    @Override
    public ShopRes.BookmarkDto bookmarkShop(Long shopId, User user) {
        BookMarkPk bookMarkPk = shopConverter.toBookMarkPk(findById(shopId), user);
        boolean isLike = existsShopBookMark(bookMarkPk);
        if(isLike){
            shopBookMarkRepository.deleteById(bookMarkPk);
            return shopConverter.toBookMarkDto(false, BOOKMARK_CANCEL_MESSAGE);
        }else{
            shopBookMarkRepository.save(shopConverter.toBookMark(bookMarkPk));
            return shopConverter.toBookMarkDto(true, BOOKMARK_REGISTER_MESSAGE);
        }
    }

    private boolean existsShopBookMark(BookMarkPk bookMarkPk) {
        return shopBookMarkRepository.existsById(bookMarkPk);
    }

    private Shop findById(Long shopId) {
         return shopRepository.findById(shopId).orElseThrow(() -> new NotFoundException(NOT_EXIST_SHOP));
    }
}
