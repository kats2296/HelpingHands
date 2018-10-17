from django.core import mail
from helpinghands.settings import EMAIL_HOST_USER


class Helper:
    @staticmethod
    def send_verification_email(email):
        print(email)
        try:
            mail.send_mail(
                "Email verification",
                "Please activate your account",
                EMAIL_HOST_USER,
                [email],
                fail_silently=False,
            )
            return 1, "Email sent!"
        except KeyError as e:
            return 2, str(e)
