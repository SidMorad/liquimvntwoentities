import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LiquimvntwoentitiesTestModule } from '../../../test.module';
import { OneUpdateComponent } from 'app/entities/one/one-update.component';
import { OneService } from 'app/entities/one/one.service';
import { One } from 'app/shared/model/one.model';

describe('Component Tests', () => {
  describe('One Management Update Component', () => {
    let comp: OneUpdateComponent;
    let fixture: ComponentFixture<OneUpdateComponent>;
    let service: OneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LiquimvntwoentitiesTestModule],
        declarations: [OneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new One(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new One();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
