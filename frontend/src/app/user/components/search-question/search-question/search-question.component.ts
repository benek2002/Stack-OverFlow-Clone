import { Component } from '@angular/core';
import { QuestionService } from '../../../user-services/question-service/question.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-search-question',
  templateUrl: './search-question.component.html',
  styleUrl: './search-question.component.css'
})
export class SearchQuestionComponent {

  titleForm!: FormGroup;
  pageNum: number = 0;
  questions: any[] = [];
  total: number;

  constructor(private questionService: QuestionService, 
              private fb: FormBuilder) {}

  ngOnInit() {
    this.titleForm = this.fb.group({
      title: [null, Validators.required]
    })
  }

  searchQuestionByTitle(){
    console.log(this.titleForm.value);
    this.questionService.searchQuestionByTitle(this.titleForm.controls['title']!.value, this.pageNum).subscribe((res) => {
      console.log(res);
      this.questions = res.questionDTOList;
      this.total = res.totalPages;
    })
  }

  pageIndexChange(event: any){
    this.pageNum = event.pageIndex;
  }

}
