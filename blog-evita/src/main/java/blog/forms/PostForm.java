package blog.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostForm {
		
		@Size(min = 3, max = 100, message = "Please enter title")
		@NotNull
		private String  title;
		
		@Size(min = 3, max = 500,  message = "Please enter content")
		@NotNull
		private String body;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}
}

