package org.scott.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.scott.service.MenuService;
import org.scott.service.dto.MenuDto;
import org.scott.service.mapstruct.MenuMapper;
import org.scott.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * project name  simple-admin-backedv1
 * filename  MenuController
 * @author liscott
 * @date 2023/2/21 18:00
 * description  TODO
 */
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @GetMapping(value = "/build")
    @Operation(summary = "获取前端所需菜单")
    public ResponseEntity<Object> buildMenues() {
        List<MenuDto> menuDtoList = menuService.findByUser(SecurityUtils.getCurrentUserId());
        List<MenuDto> menuDtos = menuService.buildTree(menuDtoList);
        return new ResponseEntity<>(menuService.buildMenus(menuDtos), HttpStatus.OK);
    }
}
