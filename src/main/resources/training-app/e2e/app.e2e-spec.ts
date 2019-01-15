import { TrainingAppPage } from './app.po';

describe('training-app App', function() {
  let page: TrainingAppPage;

  beforeEach(() => {
    page = new TrainingAppPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
