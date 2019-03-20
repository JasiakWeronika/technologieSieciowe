use HTTP::Daemon;
use HTTP::Status;
use IO::File;
  $WEBDIR = 'C:\Users\weron\Desktop\SVN\TS\sprawozdanie5';
  my $d = HTTP::Daemon->new(
           LocalAddr => 'localhost',
           LocalPort => 8080,
       )|| die;

  print "Please contact me at: <URL:", $d->url, ">\n";

  while (my $c = $d->accept) {
    while (my $r = $c->get_request) {
      if ($r->method eq 'GET') {
        $file_s = $r->uri;
        if ($file_s eq "/") {
          $file_s = "/index.html";
        }
        $c->send_file_response($WEBDIR.$file_s);
      } else {
        $c->send_error(RC_FORBIDDEN)
      }
    }
    $c->close;
    undef($c);
  }