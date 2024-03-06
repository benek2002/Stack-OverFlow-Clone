import { Component, inject } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER} from '@angular/cdk/keycodes';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

interface Tag {
  name: string;
}

@Component({
  selector: 'app-post-question',
  templateUrl: './post-question.component.html',
  styleUrls: ['./post-question.component.css']
})
export class PostQuestionComponent {
  tags: Tag[] = [];
  isSubmitting!: boolean;
  readonly separatorKeyCodes = [ENTER, COMMA] as const;
  announcer = inject(LiveAnnouncer);
  addOnBlur = true;
  validateForm!: FormGroup;

  constructor(private questionService: QuestionService, private fb: FormBuilder,
              private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      title: ['', Validators.required],
      body: ['', Validators.required],
      tags: [[], Validators.required], // Initialize tags as an empty array
    });
  }

  postQuestion() {
    console.log(this.validateForm.value);
    this.questionService.postQuestion(this.validateForm.value).subscribe((res) => {
      console.log(res);
      if( res.id != null){
        this.snackBar.open("Question posted successfully", "Close", {duration:5000});
      }else{
        this.snackBar.open("Something went wrong", "Close", {duration:5000});
      }
    })
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.tags.push({ name: value });
    }
    event.chipInput!.clear();
  }

  remove(tag: Tag): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
      this.announcer.announce(`Remove ${tag.name}`);
    }
  }

  edit(tag: Tag, event: MatChipEditedEvent): void {
    const value = event.value.trim();
    if (!value) {
      this.remove(tag);
      return;
    }
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags[index].name = value;
    }
  }
}
