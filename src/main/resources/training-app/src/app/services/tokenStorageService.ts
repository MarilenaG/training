import { Injectable } from "@angular/core";


@Injectable()
export class TokenStorageService {
    addToken(token: Object) {
        localStorage.setItem('JWT_TOKEN', JSON.stringify({ token }))
    }

    retrieveToken (){
        var currentUser = JSON.parse(localStorage.getItem('JWT_TOKEN'));
        return currentUser; // your token
    }
}