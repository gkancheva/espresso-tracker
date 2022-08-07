interface User {
  username: string;
  authData: string;
}

interface UserAuthProps {
  saveUser: (user: User) => void;
  logoutUser: () => void;
  getUser: () => User | undefined;
  isAuthenticated: () => boolean;
}

window.onbeforeunload = () => localStorage.removeItem(USER_KEY);

const USER_KEY = 'currentUser';

export const useAuth = (): UserAuthProps => {

  const saveUser = (user: User) =>
    localStorage.setItem(USER_KEY, JSON.stringify(user));

  const logoutUser = () => {
    localStorage.removeItem(USER_KEY);
    console.log("User: " + localStorage.getItem(USER_KEY));
  }

  const getUser = (): User | undefined => {
    const userAsJson = localStorage.getItem(USER_KEY);
    if (userAsJson !== undefined && userAsJson !== null) {
      return JSON.parse(userAsJson);
    }
    return undefined;
  }

  const isAuthenticated = () => {
    const user = getUser();
    return user !== null && user !== undefined;
  }

  return { saveUser, logoutUser, getUser, isAuthenticated }
}