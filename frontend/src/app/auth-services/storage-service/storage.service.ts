import { Inject, Injectable } from '@angular/core';



const TOKEN = 'c_token';
const USER = 'c_user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static hasToken(): boolean{
    if(this.getToken() == null){
      return false;
    }else{
    return true;
    }
  }


  static getToken(): string | null {
      
      if(typeof window !== "undefined"){
      return localStorage.getItem(TOKEN);
      }
      return null;
   
  }
  
  static getUserId(): string | null{
    const user = this.getUser();
    if(user == null) { return '';}
    return user.userId;
  }

  public saveUser(user: any) {

    if(typeof window !== "undefined"){
      localStorage.removeItem(USER);
      localStorage.setItem(USER, JSON.stringify(user));
    }
   
  }
  public saveToken(token: string) {

    if(typeof window !== "undefined"){
      localStorage.removeItem(TOKEN);
      localStorage.setItem(TOKEN, token);
    }
  }
  static isUserLoggedIn(){
    if(this.getToken() == null){
      return false;
    }
    return true;
  }
  static getUserRole(): string {
    const user = this.getUser();
    if(user == null){
      return '';
    }
    return user.role;
  }


  static getUser(): any {
    if(typeof window !== "undefined"){
    const userString = localStorage.getItem(USER);
    if (userString !== null) {
        return JSON.parse(userString);
    }
    }
    return null; 
}


  static logout() {
    if(typeof window !== "undefined"){

      localStorage.removeItem(TOKEN);
      localStorage.removeItem(USER);
    }
  }
  static isAdminLoggedIn(): boolean{
    if(this.getToken() === null){
      return false;
    }
    const role: string = this.getUserRole();
    return role == '1';
  }
}
