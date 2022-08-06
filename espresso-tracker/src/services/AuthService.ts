interface User {
  username: string;
  authData: string;
}

interface UserAuthProps {
  saveUser: (user: User) => void;
  user: () => User | undefined;
  isAuthenticated: () => boolean;
  authData: () => string;
}

window.onbeforeunload = () => {
  localStorage.removeItem(USER_KEY);
}

const USER_KEY = 'currentUser';

export const useAuth = (): UserAuthProps => {

  const saveUser = (user: User) => {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  const getUser = (): User | undefined => {
    const userAsJson = localStorage.getItem(USER_KEY);
    if (userAsJson === undefined || userAsJson === null) {
      return undefined;
    }
    return JSON.parse(userAsJson);
  }

  const isAuthenticated = () => {
    const user = getUser();
    console.log("user: " + JSON.stringify(user));
    return user !== null && user !== undefined;
  }

  const authData = (): string => {
    const user = getUser();
    if (user != null) {
      return user.authData;
    }
    throw new Error('User is unauthenticated');
  }

  return { saveUser, user: getUser, isAuthenticated, authData }
}