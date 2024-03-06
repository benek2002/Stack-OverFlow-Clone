import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';

@Component({
  selector: 'app-get-questions-by-userid',
  templateUrl: './get-questions-by-userid.component.html',
  styleUrl: './get-questions-by-userid.component.css'
})
export class GetQuestionsByUseridComponent {
  questions: any[] = [];
  pageNum: number = 0;
  total!: number;

  constructor(private questionService: QuestionService) {}

  ngOnInit() {
    this.getAllQuestions();
  }

  getAllQuestions(){
    this.questionService.getQuestionsByUserId(this.pageNum).subscribe((res) => {
      console.log(res);
      this.questions = res.questionDTOList;
      this.total = res.totalPages * 5;

    })
  }
  pageIndexChange(event: any) {
    this.pageNum = event.pageIndex;
    this.getAllQuestions();

  }
}
