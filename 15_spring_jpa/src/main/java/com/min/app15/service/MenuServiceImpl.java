package com.min.app15.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.app15.model.dto.CategoryDto;
import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.entity.Category;
import com.min.app15.model.entity.Menu;
import com.min.app15.model.exception.MenuNotFoundException;
import com.min.app15.repository.CategoryRepository;
import com.min.app15.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

  private final MenuRepository menuRepository;
  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;
  
  @Transactional(readOnly = true)  // 읽기 전용으로 만들면 트렌잭션이 작동하지 않는다.
  @Override
  public List<CategoryDto> findByCategoryList() {
    
    Integer categoryCode = 3;
    List<Category> categories = categoryRepository.findByCategoryCodeGreaterThan(categoryCode);
    
    return categories.stream()
                     .map(category -> modelMapper.map(category, CategoryDto.class))
                     .toList();
                     
  }

  @Override
  public MenuDto registMenu(MenuDto menuDto) {
  	
    // menuDto 를 menu 엔티티로 바꿉니다.
    Menu menu = modelMapper.map(menuDto, Menu.class);
    
    menuRepository.save(menu);  // save 는 CrudRepository가 제공하고 persist(), commit()기능을 내포하고 있음
    
    return menuDto;
    
  }
  
  @Override
  public MenuDto modifyMenu(MenuDto menuDto) throws MenuNotFoundException {
    
    // findById() 메소드가 던지는 IllegalArgumentException 처리를 하려면 아래와 같이 합니다.
    // Menu foundMenu = menuRepository.findById(menuDto.getMenuCode()).orElseThrow(() -> new IllegalArgumentException());

    // findById() 메소드의 호출 결과 엔티티는 영속 컨텍스트에 저장됩니다.
    Menu foundMenu = menuRepository.findById(menuDto.getMenuCode()).orElse(null);
    
    if(foundMenu == null)
      throw new MenuNotFoundException("해당 메뉴가 없습니다.");
    
    // 영속 컨텍스트에 저장된 엔티티를 변경하면 해당 변경 사항이 데이터베이스에 반영됩니다.
    foundMenu.setMenuName(menuDto.getMenuName());
    foundMenu.setMenuPrice(menuDto.getMenuPrice());
    foundMenu.setCategoryCode(menuDto.getCategoryCode());
    foundMenu.setOrderableStatus(menuDto.getOrderableStatus());
    
    return menuDto;
    
  }
  
  @Override
  public void deleteMenu(Integer menuCode) throws MenuNotFoundException {
    
    // menuRepository.deleteById(menuCode);  // 없는 메뉴는 어떻게 할 것인가? 처리할 수 없기 때문에 find 기반 delete 로 바꿉니다.
    
    Menu foundMenu = menuRepository.findById(menuCode).orElse(null);
    
    if(foundMenu == null)
      throw new MenuNotFoundException("해당 메뉴가 없습니다.");
    
    menuRepository.delete(foundMenu);
    
  }

  @Transactional(readOnly = true)  // 읽기 전용으로 만들면 트렌잭션이 작동하지 않는다.
  @Override
  public MenuDto findMenuById(Integer menuCode) {
    
    // 연습을 위해서 MenuNotFoundException 대신 findById() 메소드가 던지는 IllegalArgumentException 예외로 처리합니다.
    
    Menu foundMenu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
    
    return modelMapper.map(foundMenu, MenuDto.class);
    
  }
  
  @Transactional(readOnly = true)
  @Override
  public List<MenuDto> findMenuList(Pageable pageable) {
  
    Page<Menu> menuList = menuRepository.findAll(pageable);
    
    return menuList.map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList();  // Immutable List 를 반환합니다.
    
  }
  
  @Transactional(readOnly = true)
  @Override
  public List<MenuDto> findByMenuPrice(Integer menuPrice) {
    
    // List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqual(menuPrice);
    // List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqual(menuPrice, Sort.by("menuPrice").descending());
    List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(menuPrice);
    
    return menuList.stream()
                   .map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList();
    
  }
  
  @Transactional(readOnly = true)
  @Override
  public List<MenuDto> findByMenuName(String menuName) {
    
    List<Menu> menuList = menuRepository.findByMenuNameContaining(menuName);
    
    return menuList.stream()
                   .map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList();
    
  }
  
}
