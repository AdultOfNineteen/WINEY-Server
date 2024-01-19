package com.example.wineyinfrastructure.amazonS3.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Folder {
	WINE_TIP("wine-tip/"),
	TASTING_NOTE("tasting-note/"),
	USER("user/"),
	SHOP("shop/");

	private final String folderName;
}
