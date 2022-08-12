import MenuIcon from "@mui/icons-material/Menu";
import IconButton from "@mui/material/IconButton";
import { Menu, MenuItem } from "@mui/material";
import * as React from "react";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { CoffeeTool, CoffeeTools, CoffeeToolType, useCoffeeToolsService } from "../services/CoffeeToolsService";
import { UpdateCoffeeToolsDialog } from "../components/UpdateCoffeeToolsDialog";
import { useAuth } from "../services/AuthService";

import { GlobalNotificationProps } from "../components/Notification";

export const MenuButton = ({ showNotification }: GlobalNotificationProps) => {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();

  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [menuVisible, setMenuVisible] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [coffeeTools, setCoffeeTools] = useState<CoffeeTools>();
  const [clickedToolType, setClickedToolType] = useState<CoffeeToolType>();
  const [clickedTool, setClickedTool] = useState<CoffeeTool | undefined>();

  useEffect(() => {
    if (menuVisible) {
      getCoffeeTools();
    }
  }, [menuVisible]);

  const { getCoffeeTools } = useCoffeeToolsService(
    (data) => setCoffeeTools(data),
    (err) => console.log(err)
  );

  const showMenu = (event: React.MouseEvent<HTMLButtonElement>) => {
    if (!isAuthenticated()) {
      showNotification('You need to login to access the menu', 'warning');
      navigate('/login');
      return;
    }
    setAnchorEl(event.currentTarget);
    setMenuVisible(true);
  };

  const hideMenu = () => {
    setAnchorEl(null);
    setMenuVisible(false);
  };

  const showEditingDialog = (toolType: CoffeeToolType, tool?: CoffeeTool) => {
    setClickedToolType(toolType);
    setClickedTool(tool);
    setDialogOpen(true);
    hideMenu();
  }

  const getModelNameText = (coffeeTool?: CoffeeTool) => {
    if (coffeeTool && coffeeTool.name) {
      return ` : ${coffeeTool.name}`;
    }
    return ': N/A';
  }

  return (
    <>
      <IconButton
        size='large'
        edge='start'
        color='inherit'
        aria-label='menu'
        sx={{mr: 2}}
        onClick={menuVisible ? hideMenu : showMenu}>
        <MenuIcon/>
      </IconButton>
      <Menu
        id='basic-menu'
        anchorEl={anchorEl}
        open={menuVisible}
        onClose={hideMenu}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}>
        <MenuItem key={CoffeeToolType.COFFEE_MACHINE} onClick={() =>
          showEditingDialog(CoffeeToolType.COFFEE_MACHINE, coffeeTools?.coffeeMachine)}>
          Coffee machine {getModelNameText(coffeeTools?.coffeeMachine)}
        </MenuItem>
        <MenuItem key={CoffeeToolType.GRINDER} onClick={() => showEditingDialog(CoffeeToolType.GRINDER, coffeeTools?.grinder)}>
          Grinder {getModelNameText(coffeeTools?.grinder)}
        </MenuItem>
      </Menu>
      {
        clickedToolType &&
        <UpdateCoffeeToolsDialog
          showNotification={showNotification}
          type={clickedToolType}
          tool={clickedTool}
          dialogIsOpen={dialogOpen}
          onCloseDialog={() => setDialogOpen(false)}
          onFinish={(data) => {
            setDialogOpen(false);
            setCoffeeTools(data);
          }}
        />
      }
    </>
  );
}