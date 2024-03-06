import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from '../../../auth-services/storage-service/storage.service';
import { Observable } from 'rxjs';


const BASIC_URL = ["http://localhost:8080/"];

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private httpClient: HttpClient) { }


  createAuthorizationHeader(): HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      "Authorization", "Bearer " + StorageService.getToken()
    );
  }

  postAnswer(answerDTO: any): Observable<any> {

    return this.httpClient.post<[]>(BASIC_URL + "api/answer", answerDTO,{
      headers: this.createAuthorizationHeader()
    }
    );
  }

  
  postAnswerImage(file: any, answerId: number): Observable<any> {
    
    return this.httpClient.post<[]>(BASIC_URL + `api/image/${answerId}`, file, 
    {
      headers: this.createAuthorizationHeader()
    }
    );
  }

  approveAnswer(answerId: number): Observable<any> {
    
    return this.httpClient.get<[]>(BASIC_URL + `api/answer/${answerId}`, 
    {
      headers: this.createAuthorizationHeader()
    }
    );
  }

  addVoteToAnswer(answerVoteDTO: any): Observable<any> {
    
    return this.httpClient.post<[]>(BASIC_URL + `api/answer-vote`, answerVoteDTO, 
    {
      headers: this.createAuthorizationHeader()
    }
    );
  }

  postCommentToAnswer(commentDTO: any): Observable<any> {
    
    return this.httpClient.post<[]>(BASIC_URL + `api/answer/comment`, commentDTO, 
    {
      headers: this.createAuthorizationHeader()
    }
    );
  }




}
