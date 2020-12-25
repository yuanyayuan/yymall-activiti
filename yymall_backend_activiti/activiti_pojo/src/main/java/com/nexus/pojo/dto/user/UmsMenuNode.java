package com.nexus.pojo.dto.user;


import com.nexus.pojo.UmsMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @className UmsMenuNode
 * @description 后台菜单节点封装
 * @author LiYuan
 * @date 2020/12/4
**/
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<UmsMenuNode> children;
}
