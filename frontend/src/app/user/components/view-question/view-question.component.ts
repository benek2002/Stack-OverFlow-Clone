import { Component, RendererStyleFlags2 } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../auth-services/storage-service/storage.service';
import { AnswerService } from '../../user-services/answer-services/answer.service';
import { read } from 'fs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-question',
  templateUrl: './view-question.component.html',
  styleUrl: './view-question.component.css'
})
export class ViewQuestionComponent {

  qustionid: number = this.activatedRoute.snapshot.params["id"];
  question: any;
  validateForm!: FormGroup;

  selectedFile!: File | null;
  imagePreview!: string | ArrayBuffer | null;
  formData: FormData = new FormData();
  answers = [];
  displayButton: boolean = false;

  constructor(private questionService: QuestionService,
    private answerService: AnswerService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder, 
    private snackBar: MatSnackBar) {}


    ngOnInit(): void {
      this.validateForm = this.fb.group({
        body: [null, Validators.required]
      })

      this.getQuestionById();
    }

    onFileSelected(event: any){
      this.selectedFile = event.target.files[0];
      this.previewImage();
    }

    previewImage() {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile!);
    }

    getQuestionById(){
      this.questionService.getQuestionById(this.qustionid).subscribe((res) => {
        console.log(res);
        this.question = res.questionDTO;
        res.answerDTOList.forEach(element=> {
          if(element.file != null){
          element.convertedImg = 'data:image/jpeg;base64,' + element.file.data;      
        } 
        this.answers.push(element);
        });
        if(StorageService.getUserId() == this.question.userId){
          this.displayButton = true;
        }
    });
    }

    addVote(voteType: string, voted: number){
        if(voted == 1 || voted == -1){
          this.snackBar.open("You are already voted to this question", "Close", {duration:5000, panelClass: 'error-snackbar'});
        }else{
          const data = {
            voteType: voteType,
            userId: StorageService.getUserId(),
            questionId:this.qustionid
  
          }
          this.questionService.addVoteToQuestion(data).subscribe((res) =>{
            console.log(res);
            if(res.id != null){
              this.snackBar.open("Vote added successfully", "Close", {duration:5000});
              this.getQuestionById();
            }else{
              this.snackBar.open("Something went wrong", "Close", {duration:5000});
            }
          })
        }
  
        
      }

      approveAnswer(answerId:number){
        this.answerService.approveAnswer(answerId).subscribe((res) => {
          console.log(res);
          if(res.id != null){
            this.snackBar.open("Answer approved successfully", "Close", {duration:5000});
            this.getQuestionById();
          }else{
            this.snackBar.open("Something went wrong", "Close", {duration:5000});
          }
        })
      }
    

    addAnswer() {
      console.log(this.validateForm.value);
      const data = this.validateForm.value;
      data.questionId = this.qustionid;
      data.userId = StorageService.getUserId();
      console.log(data);
      this.formData.append("multipartFile", this.selectedFile!);
      this.answerService.postAnswer(data).subscribe((res1) => {
        this.answerService.postAnswerImage(this.formData, res1.id).subscribe((res2) => {
          console.log("Post answer Image API", res2);
        })
        console.log("Post Answer API Rsponse", res1);
        if(res1.id != null){
          this.snackBar.open("Answer posted successfully", "Close", {duration: 5000});
        }else {
          this.snackBar.open("Something went wrong", "Close", {duration: 5000});
        }
      })

    }

    addVoteToAnswer(voteType: string, answerId: number, voted: number){
      if(voted == 1 || voted == -1){
        this.snackBar.open("You already voted to this answer", "Close", {duration: 5000});
      }else{

        const answerVoteDTO = {
          voteType: voteType,
          userId: StorageService.getUserId(),
          answerId: answerId
  
        }
        this.answerService.addVoteToAnswer(answerVoteDTO).subscribe((res) => {
          console.log(res);
          if(res.id != null){
            this.snackBar.open("Vote added successfully", "Close", {duration: 5000});
          }else{
            this.snackBar.open("Something went wrong", "Close", {duration: 5000});
          }
        })
      }
     

  
    }

    postComment(answerId: number, comment: string){
      console.log(answerId, comment);
      const commentDTO = {
        body: comment,
        answerId: answerId,
        userId: StorageService.getUserId()
      }
      this.answerService.postCommentToAnswer(commentDTO).subscribe((res)=>{
        console.log(res);
        if(res.id != null){
          this.snackBar.open("Comment added successfully", "Close", {duration: 5000});
          this.answers = [];
          this.getQuestionById();
        }else{
          this.snackBar.open("Something went wrong", "Close", {duration: 5000});
        }
      })
    }

}
