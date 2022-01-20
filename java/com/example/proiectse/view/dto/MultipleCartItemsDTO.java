package com.example.proiectse.view.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleCartItemsDTO {
    private List<CartItemDTO> cartItemDTOList;
    private String email;
}
