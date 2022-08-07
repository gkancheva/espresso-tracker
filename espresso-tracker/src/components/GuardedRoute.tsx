import { useAuth } from "../services/AuthService";
import React from "react";
import { Navigate } from "react-router";

interface GuardedRouteProps {
  component: React.ReactNode;
}

export const GuardedRoute = ({ component }: GuardedRouteProps) => {
  const { isAuthenticated } = useAuth();

  return (
    <>
      { isAuthenticated() ? component : <Navigate replace to={'/login'} /> }
    </>
  );

}

