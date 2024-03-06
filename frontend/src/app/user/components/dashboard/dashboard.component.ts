import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  questions: any[] = [];
  pageNum: number = 0;
  total!: number;

  constructor(private questionService: QuestionService) {}

  ngOnInit() {
    this.getAllQuestions();
  }

  getAllQuestions(){
    this.questionService.getAllQuestions(this.pageNum).subscribe((res) => {
      console.log(res);
      this.questions = res.questionDTOList;
      this.total = res.totalPages * 5;

    })
  }
  pageIndexChange(event: any) {
    this.pageNum = event.pageIndex;
    this.getAllQuestions();

  }

  getLatestQuestions(){
    console.log("Radio Working ...");
    this.questionService.getLatestQuestions(this.pageNum).subscribe((res) => {
      console.log(res);
      this.questions = res.questionDTOList;
      this.total = res.totalPages * 5;
    })

  }

}
