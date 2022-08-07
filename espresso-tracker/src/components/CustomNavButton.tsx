import Button from "@mui/material/Button";
import * as React from "react";
import { useAuth } from "../services/AuthService";
import { useNavigate } from "react-router";

export enum ViewAccessType {
  UNAUTHENTICATED, AUTHENTICATED
}

interface Props {
  href: string;
  children?: React.ReactNode;
  text: string;
  viewAccess?: ViewAccessType;
  onClick?: () => void;
}

export const CustomNavButton = ({ href, children, text, viewAccess, onClick }: Props) => {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();

  const showNavButton = () => {
    switch (viewAccess) {
      case ViewAccessType.AUTHENTICATED: return isAuthenticated();
      case ViewAccessType.UNAUTHENTICATED: return !isAuthenticated();
      default: return true;
    }
  }

  return (
    <>
      { showNavButton() &&
        <Button onClick={() => {
          onClick && onClick();
          navigate(href);
        }} color='inherit'>
          {text} {children}
        </Button>
      }
    </>
  );
}