import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { UserRoutingModule } from './user-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostQuestionComponent } from './components/post-question/post-question.component';

import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatPaginatorModule} from '@angular/material/paginator';
import { ViewQuestionComponent } from './components/view-question/view-question.component';
import { GetQuestionsByUseridComponent } from './components/get-questions-by-userid/get-questions-by-userid.component';
import { SearchQuestionComponent } from './components/search-question/search-question/search-question.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatRadioButton, MatRadioModule} from '@angular/material/radio';



@NgModule({
  declarations: [
    DashboardComponent,
    PostQuestionComponent,
    ViewQuestionComponent,
    GetQuestionsByUseridComponent,
    SearchQuestionComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule, 
    HttpClientModule,
    ReactiveFormsModule, 
    FormsModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatChipsModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule, 
    MatCardModule,
    MatPaginatorModule, 
    MatDividerModule,
    MatRadioModule

  ]
})
export class UserModule { }
