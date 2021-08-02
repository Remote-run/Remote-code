enum LoginLevel {
  LOGGED_IN,
  LOGGED_OUT
}

class AuthServiceC {
  private loginLevel: LoginLevel = LoginLevel.LOGGED_OUT;
  private jwtB64String: string | null = null;
  private jwtObj: { groups: string[] } | null = null;

  private jwtTokenKey = 'JWT_TOKEN'

  constructor () {
    const maybeToken = this.getTokenFromStorage()

    if (maybeToken !== null && maybeToken !== '') {
      this.loginLevel = LoginLevel.LOGGED_IN
      this.jwtB64String = maybeToken
      this.jwtObj = this.parseToken(maybeToken)
    }
  }

  public isAdmin () {
    if (this.jwtObj == null) {
      return false
    }
    try {
      return this.jwtObj.groups.includes('admin')
    } catch (e) {
      return false
    }
  }

  public isLoggedIn (): boolean {
    return this.loginLevel === LoginLevel.LOGGED_IN
  }

  public getToken (): string | null {
    return this.jwtB64String
  }

  public logIn (token: string) {
    console.log('SAVING TOKEN')
    console.log(token)
    this.loginLevel = LoginLevel.LOGGED_IN
    this.saveTokenToStorage(token)
    this.jwtB64String = token
    this.jwtObj = this.parseToken(token)
    console.log(this.jwtObj)
  }

  public logOut () {
    this.loginLevel = LoginLevel.LOGGED_OUT
    this.jwtB64String = null
    this.jwtObj = null
    localStorage.removeItem(this.jwtTokenKey)
  }

  private getTokenFromStorage (): string | null {
    const maybeToken = localStorage.getItem(this.jwtTokenKey)
    return maybeToken
  }

  private saveTokenToStorage (token: string) {
    localStorage.setItem(this.jwtTokenKey, token)
  }

  private parseToken (token: string): any {
    try {
      return JSON.parse(atob(token.replace('Bearer ', '').split('.')[1]))
    } catch (e) {
      return null
    }
  }
}

export const AuthService = new AuthServiceC()
